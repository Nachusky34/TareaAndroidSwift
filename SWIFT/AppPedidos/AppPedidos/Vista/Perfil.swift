//
//  Perfil.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

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
                    Image(systemName: "person.circle")
                        .resizable()
                        .frame(width: 150, height: 150)
                        .foregroundColor(.white)
                        
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
                        .padding(.top, 60)
                        .onAppear {
                            subscription = usuario.newsletter ?? false
                        }
                    
                    HStack {
                        Spacer() // Empuja el botón hacia el centro verticalmente
                        Button(action: {
                            // Acción de cerrar sesión
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
                        .padding(.top, 30)
                        Spacer() // Empuja el botón hacia el centro verticalmente
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
