//
//  Registro.swift
//  AppPedidos
//
//  Created by Usuario invitado on 18/3/25.
//
import SwiftUI

struct Registro: View {
    @State private var usuarios: [Usuario] = [] // Se inicializa vacío
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var correo: String = ""
    @State private var postalCode: String = ""
    @State private var mostrarAlerta: Bool = false
    @State private var mensajeAlerta: String = ""
    @State private var navigateToLogin: Bool = false

    var body: some View {
        NavigationStack {
            VStack {
                Image(.imgDeco3)
                    .resizable()
                    .frame(width: 225, height: 225)
                    .padding(.leading, 177)
                    .padding(.top, -79)
                
                Text("PC\nera")
                    .font(.custom("Times New Roman", size: 55))
                    .fontWeight(.bold)
                    .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                    .padding(.trailing, 160)
                    .padding(.top, -140)
                
                Image(.logoCircular)
                    .resizable()
                    .frame(width: 100, height: 100)
                    .padding(.top, -40)
                
                VStack {
                    Text("SIGN UP")
                        .font(.custom("Times New Roman", size: 32))
                        .fontWeight(.bold)
                    
                    VStack(alignment: .leading) {
                        TextField("Username", text: $username)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                            .padding(.bottom, 15)
                            .autocapitalization(.none)
                        
                        SecureField("Password", text: $password)
                            .font(.custom("Times New Roman", size: 25))
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .padding(.bottom, 15)
                            .autocapitalization(.none)
                        
                        TextField("Email", text: $correo)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                            .padding(.bottom, 15)
                            .autocapitalization(.none)
                        
                        TextField("Postal Code", text: $postalCode)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                            .autocapitalization(.none)
                        
                        HStack {
                            Spacer()
                            
                            Text("Already have an account?")
                                .font(.custom("Times New Roman", size: 15))
                            
                            NavigationLink(destination: Login().navigationBarBackButtonHidden(true)) {
                                Text("SIGN IN")
                                    .font(.custom("Times New Roman", size: 15))
                                    .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                            }
                            
                            Spacer()
                        }
                        .padding(.top, 10)
                        
                    }
                }
                .padding(.horizontal, 45)
                .padding(.bottom, 0)

                Button(action: {
                    // Verificar si el username ya existe en la lista de usuarios
                    if validarCampos() {
                        if usuarios.contains(where: { $0.username == username }) {
                            mensajeAlerta = "This username is already in use"
                            mostrarAlerta = true
                        } else {
                            //guardarUsuarioJson(username: username, password: password, email: correo, postalcode: postalCode)
                            usuarios = CargarDatosUsuario() // Recargar usuarios después de registrar
                            navigateToLogin = true
                        }
                    }
                }) {
                    Text("SIGN UP")
                        .font(.custom("Times New Roman", size: 18))
                        .frame(width: 90, height: 20)
                        .padding()
                        .background(Color.blue.opacity(0.2))
                        .foregroundColor(.blue)
                        .cornerRadius(10)
                }
                .background(Color.clear)
                .contentShape(Rectangle())
                .padding(.horizontal, 100)
                .padding(.leading, 169)
                .padding(.bottom, -70)
                .padding(.top, 20)
                
                .alert(isPresented: $mostrarAlerta) {
                    Alert(
                        title: Text("Error"),
                        message: Text(mensajeAlerta),
                        dismissButton: .default(Text("Try Again"))
                    )
                }
                
                NavigationLink("", destination: Login().navigationBarBackButtonHidden(true), isActive: $navigateToLogin)
                    .hidden()
                
                Image(.imgDeco4)
                    .resizable()
                    .frame(width: 225, height: 225)
                    .padding(.trailing, 177)
                    .padding(.bottom, -51)
            }
            .onAppear {
                usuarios = CargarDatosUsuario() // Cargar usuarios al abrir la pantalla
            }
            .padding()
        }
    }
    
    func validarCampos() -> Bool {
            if username.isEmpty || password.isEmpty || correo.isEmpty || postalCode.isEmpty {
                mensajeAlerta = "All fields are required"
                mostrarAlerta = true
                return false
            }
            return true
        }
}

#Preview {
    Registro()
}
