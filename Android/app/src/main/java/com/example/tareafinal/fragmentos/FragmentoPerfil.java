package com.example.tareafinal.fragmentos;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tareafinal.R;
import com.example.tareafinal.UI.Tabs;
import com.example.tareafinal.db.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private LinearLayout layoutCerrarSesion;
    private Switch newsletterSwitch;
    private Usuario usuario;
    private String idUser;

    private FirebaseDatabase database;
    private DatabaseReference dbReferenceUsuario;
    private String ruta;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance("https://pcera-2b2f4-default-rtdb.europe-west1.firebasedatabase.app/");
        dbReferenceUsuario = database.getReference("usuarios");
        ruta = "/data/data/com.example.tareafinal/files/fotoPerfil/foto_perfil_"; //Ruta de la imagen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        username = view.findViewById(R.id.tv_username);
        email = view.findViewById(R.id.tv_email);
        postalcode = view.findViewById(R.id.tv_postalCode);
        newsletterSwitch = view.findViewById(R.id.switch_newsletter);
        imagePerfil = view.findViewById(R.id.iv_user);
        layoutCerrarSesion = view.findViewById(R.id.layout_cerrar_sesion);

        newsletterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dbReferenceUsuario.child(usuario.getId()).child("newsletter").setValue(true);
            } else {
                dbReferenceUsuario.child(usuario.getId()).child("newsletter").setValue(false);
            }
        });

        layoutCerrarSesion.setOnClickListener(v -> cerrarSesion(v));

        View layoutHacerFoto = view.findViewById(R.id.layout_hacer_foto);
        layoutHacerFoto.setOnClickListener(v -> hacerFoto(v));

        usuario = (Usuario) getArguments().getSerializable("usuario");
        System.out.println(usuario.getId());

        if (usuario != null) {
            idUser = usuario.getId();
        }

        cargarDatosUsuario();

        return view;
    }

    private void cargarDatosUsuario() {

        dbReferenceUsuario.orderByChild("id").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Usuario usuario = userSnapshot.getValue(Usuario.class);
                        if (usuario != null) {
                            username.setText(usuario.getUsername());
                            email.setText(usuario.getEmail());
                            postalcode.setText(usuario.getPostalCode());
                            newsletterSwitch.setChecked(usuario.isNewsletter());

                            cargarImagenDesdeRutaAbsoluta(ruta);
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

    public void hacerFoto(View view) {
        Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fotoLauncher.launch(intentFoto);
    }

    private void subirImagenAFirebase(Bitmap imageBitmap) {
        // Generar el nombre de la imagen dinámicamente
        String nombreImagen = "foto_perfil_" + idUser;
        guardarImagenEnInterno(imageBitmap, nombreImagen);

        // Subir imagen a Firebase Storage
        DatabaseReference userRef = dbReferenceUsuario.child(idUser).child("fotoPerfil");
        userRef.setValue(nombreImagen)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Imagen alamcenada", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al guardar nombre", Toast.LENGTH_SHORT).show());
    }


    private void cargarImagenDesdeRutaAbsoluta(String rutaImagen) {
        File archivoImagen = new File(rutaImagen + idUser + ".png");

        if (archivoImagen.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(archivoImagen.getAbsolutePath());
            imagePerfil.setImageBitmap(bitmap);
        } else {
            // Si no encuentra la imagen, pone la de defecto
            int idImagen = getResources().getIdentifier("foto_perfil_nacho", "drawable", getContext().getPackageName());

            if (idImagen != 0) { // Si la imagen existe
                imagePerfil.setImageResource(idImagen);
            } else {
                imagePerfil.setImageResource(R.drawable.icono_perfil_blanco); // Imagen por defecto si no se encuentra
            }
        }
    }


    private String guardarImagenEnInterno(Bitmap bitmap, String nombreArchivo) {
        File directorio = new File(getContext().getFilesDir(), "fotoPerfil"); // Carpeta de almacenamiento interno (fotoPerfil)

        //Crea la carpeta si no existe
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        File archivoImagen = new File(directorio, nombreArchivo + ".png");

        try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return archivoImagen.getAbsolutePath(); // Retorna la ruta donde se guardó la imagen
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cerrarSesion(View v) {
        // Obtener la actividad que contiene este fragmento
        if (getActivity() instanceof Tabs) {
            Tabs tabsActivity = (Tabs) getActivity();  // Hacer el casting seguro
            tabsActivity.cerrarSesion();  // Llamar al método de cerrar sesión de la actividad
        }
    }

}

