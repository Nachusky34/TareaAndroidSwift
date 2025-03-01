//
//  Login.swift
//  AppPedidos
//
//  Created by Mario Seoane on 13/2/25.
//

import SwiftUI

struct Login: View {
    let usuarios: [Usuario] = CargarDatosUsuario()
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var mostrarAlerta: Bool = false
    @State private var isAuthenticated: Bool = false
    @State private var usuarioAutenticado: Usuario? = nil
    
    var body: some View {
        NavigationStack {
            if isAuthenticated, let usuario = usuarioAutenticado {
                Tab(usuario: Binding(
                    get: { usuario },
                    set: { usuarioAutenticado = $0 }
                ))
                .navigationBarBackButtonHidden(true)
            } else {
                VStack {
                    Image(.imgDeco2)
                        .resizable()
                        .frame(width: 225, height: 225)
                        .padding(.trailing, 204)
                        .padding(.top, -79)
                    
                    Text("NOMBRE\nAPP")
                        .font(.custom("Times New Roman", size: 38))
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .foregroundColor(.blue)
                        .padding(.leading, 140)
                        .padding(.top, -120)
                    
                    Spacer()
                    
                    VStack {
                        Text("SIGN IN")
                            .font(.custom("Times New Roman", size: 32))
                            .fontWeight(.bold)
                        
                        VStack(alignment: .leading) {
                            TextField("Username", text: $username)
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                                .font(.custom("Times New Roman", size: 25))
                                .padding(.bottom, 20)
                                .padding(.top, 30)
                                .autocapitalization(.none)
                            
                            SecureField("Password", text: $password)
                                .font(.custom("Times New Roman", size: 25))
                                .textFieldStyle(RoundedBorderTextFieldStyle())
                                .autocapitalization(.none)
                        }
                    }
                    .padding(.horizontal, 45)
                    .padding(.bottom, 100)

                    Button(action: {
                        if let usuarioValido = usuarios.first(where: { $0.username == username && $0.password == password }) {
                            usuarioAutenticado = usuarioValido
                            isAuthenticated = true
                            mostrarAlerta = false
                            
                        } else {
                            mostrarAlerta = true
                        }
                    }) {
                        Text("SIGN IN")
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
                    .padding(.trailing, 160)
                    .padding(.bottom, -60)
                    
                    .alert(isPresented: $mostrarAlerta) {
                        Alert(
                            title: Text("Error"),
                            message: Text("Username or password incorrect"),
                            dismissButton: .default(Text("OK"))
                        )
                    }
                    
                    Image(.imgDeco1)
                        .resizable()
                        .frame(width: 225, height: 225)
                        .padding(.leading, 204)
                        .padding(.bottom, -51)
                }
                .padding()
            }
        }
    }
}


#Preview {
    Login()
}
