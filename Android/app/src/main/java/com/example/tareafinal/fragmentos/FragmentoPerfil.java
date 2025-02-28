package com.example.tareafinal.fragmentos;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoPerfil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView username, email, postalcode;
    private ImageView imagePerfil;
    private Switch newsletter;
    private Usuario usuario;
    private String idUser;

    private FirebaseDatabase database;
    private DatabaseReference dbReferencePerfil;

    FirebaseStorage storage;
    StorageReference storageRef;
    ByteArrayOutputStream outputStream;

    ActivityResultLauncher<Intent> fotoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Obtener el Bitmap directamente desde el Intent
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data"); // Captura la miniatura
                            if (imagePerfil != null) { // Verificar que no sea null
                                imagePerfil.setImageBitmap(imageBitmap); // Muestra la imagen en el ImageView
                                if (imageBitmap != null) {
                                    subirImagenAFirebase(imageBitmap);
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "No se tomó ninguna foto.", Toast.LENGTH_SHORT).show();
                }
            });

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoPerfil newInstance(String param1, String param2) {
        FragmentoPerfil fragment = new FragmentoPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        username = view.findViewById(R.id.tv_username);
        email = view.findViewById(R.id.tv_email);
        postalcode = view.findViewById(R.id.tv_postalCode);
        newsletter = view.findViewById(R.id.switch_newsletter);
        imagePerfil = view.findViewById(R.id.iv_user);

        View layoutHacerFoto = view.findViewById(R.id.layout_hacer_foto);
        layoutHacerFoto.setOnClickListener(v -> hacerFoto(v));

        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReferencePerfil = database.getReference("usuarios");

        usuario = (Usuario) getArguments().getSerializable("usuario");
        if (usuario != null) {
            idUser = usuario.getId();
        }

        cargarDatosUsuario();

        return view;
    }

    private void cargarDatosUsuario() {

        dbReferencePerfil.orderByChild("id").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Usuario usuario = userSnapshot.getValue(Usuario.class);
                        if (usuario != null) {
                            username.setText(usuario.getUsername());
                            email.setText(usuario.getEmail());
                            postalcode.setText(usuario.getPostalCode());
                            newsletter.setChecked(usuario.isNewsletter());

                            cargarImagenDesdeDrawable(usuario.getFotoPerfil());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error de base de datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hacerFoto(View view){
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fotoLauncher.launch(intentFoto);
    }

    private void subirImagenAFirebase(Bitmap imageBitmap) {
        // Generar el nombre de la imagen dinámicamente
        String nombreImagen = "foto_perfil_" + idUser;

        // Convertir Bitmap a bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] data = outputStream.toByteArray();

        // Subir imagen a Firebase Storage
        DatabaseReference userRef = dbReferencePerfil.child(idUser).child("fotoPerfil");
        userRef.setValue(nombreImagen)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Imagen alamcenada", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al guardar nombre", Toast.LENGTH_SHORT).show());
    }


    private void cargarImagenDesdeDrawable(String nombreImagen) {
        Context context = getContext();
        if (context != null) {
            int imageResource = context.getResources().getIdentifier(nombreImagen, "drawable", context.getPackageName());

            if (imageResource != 0) {
                imagePerfil.setImageResource(imageResource);
            } else {
                // si no encuentra la imagen ponemos la de defecto
                imagePerfil.setImageResource(R.drawable.icono_perfil_blanco);
            }
        }
    }
}