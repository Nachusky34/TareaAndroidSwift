//
//  Perfil.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//
import UIKit
import SwiftUI

struct Perfil: View {
    
    @Binding var usuario: Usuario
    
    @State private var username: String = ""
    @State private var email: String = ""
    @State private var postalCode: String = ""
    @State private var subscription: Bool = false
    
    var body: some View {
        VStack {
            ZStack{
                Rectangle()
                    .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                    .frame(width: 405, height: 350)
                    .padding(.top, -78)
                VStack{
                    
                    // lo hacemos de esta manera, que sabemos que es inefeciente pero para que nos carge la imagen correcta, no encuentra las fotos desde el json
                    if usuario.username.elementsEqual("marioSeoane") {
                        Image(.fotoMario)
                            .resizable()
                            .frame(width: 150, height: 150)
                            .foregroundColor(.white)
                    } else if usuario.username.elementsEqual("nachusky34") {
                        Image(.fotoNacho)
                            .resizable()
                            .frame(width: 150, height: 150)
                            .foregroundColor(.white)
                    } else {
                        Image(systemName: "person.circle")
                            .resizable()
                            .frame(width: 150, height: 150)
                            .foregroundColor(.white)
                    }
                    
                    
                    
                    Text(usuario.username ?? "maritotito")
                        .font(.custom("Times New Roman", size: 32))
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .padding(.top, 20)
                }
            }
            .padding(.bottom, 80)
            
            VStack {
                VStack(alignment: .leading) {
                    HStack{
                        ZStack{
                            RoundedRectangle(cornerRadius: 10)
                                .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                                .frame(width: 30, height: 30)
                                
                            Image(systemName: "at")
                                .foregroundColor(.white)
                        }

                        TextField("Email", text: $email)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                            .onAppear {
                                email = usuario.email ?? "mario@hola.es"
                            }
                    }
                    .padding(.bottom, 30)
                
                    HStack{
                        ZStack{
                            RoundedRectangle(cornerRadius: 10)
                                .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                                .frame(width: 30, height: 30)
                                
                            Image(systemName: "envelope")
                                .foregroundColor(.white)
                        }

                        TextField("Postal Code", text: $postalCode)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                            .onAppear {
                                postalCode = String(usuario.postalcode ?? "12345")
                            }
                    }
                    
                    Toggle("Newsletter subscription:", isOn: $subscription)
                        .font(.custom("Times New Roman", size: 23))
                        .padding(.top, 20)
                        .onAppear {
                            subscription = usuario.newsletter ?? false
                        }
                    
                    HStack {
                        Spacer()
                        Button(action: {
                            // aqui guarda los cambios del usuario (el correo y el codigo postal)
                            let usuarioActualizado = Usuario(
                                    id: usuario.id,
                                    username: usuario.username,
                                    password: usuario.password,
                                    email: email,
                                    postalcode: postalCode,
                                    newsletter: subscription,
                                    foto: usuario.foto
                                )
                            
                                guardarCambiosUsuario(usuario: usuarioActualizado)
                                usuario = usuarioActualizado // Actualizar el usuario en la vista
                        }) {
                            HStack {
                                Text("SAVE CHANGES")
                                    .padding(.leading, 15)
                                
                                Image(systemName: "checkmark.icloud")
                                    .padding(.leading, 10)
                                
                            }.font(.custom("Times New Roman", size: 18))
                                .frame(width: 200, height: 20)
                                .padding()
                                .background(Color.green.opacity(0.9))
                                .foregroundColor(.black)
                                .fontWeight(.bold)
                                .cornerRadius(10)
                        }
                        .padding(.top, 30)
                        Spacer()
                    }
                    
                    HStack {
                        Spacer()
                        Button(action: {
                            usuario = Usuario(id: -1, username: "", password: "", email: "", postalcode: "", newsletter: false, foto: "")
                        }) {
                            HStack {
                                Text("SIGN OUT")
                                    .padding(.leading, 35)
                                
                                Image(systemName: "rectangle.portrait.and.arrow.right")
                                    .padding(.leading, 10)
                                
                            }.font(.custom("Times New Roman", size: 18))
                                .frame(width: 200, height: 20)
                                .padding()
                                .background(Color.red.opacity(0.9))
                                .foregroundColor(.black)
                                .fontWeight(.bold)
                                .cornerRadius(10)
                        }
                        .padding(.top, 10)
                        Spacer()
                    }

                }
            }
                .padding(.horizontal, 25)
            
            Spacer()
        }
        .padding()
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Perfil(usuario: .constant(usuarioFicticio))
}
