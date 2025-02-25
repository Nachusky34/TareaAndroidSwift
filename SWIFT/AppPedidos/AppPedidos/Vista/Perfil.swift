//
//  Perfil.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

import SwiftUI

struct Perfil: View {
    @EnvironmentObject var usuario: UserData
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
                        
                    Text($usuario.username) // Muestra el nombre de usuario aquí
                        .font(.custom("Times New Roman", size: 32))
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                        .padding(.top, 20)
                }
            }
            .padding(.bottom, 100)
            
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
                                email = usuario.email
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
                                postalCode = String(usuario.postalcode)
                            }
                    }
                    
                    Toggle("Newsletter subscription:", isOn: $subscription)
                        .font(.custom("Times New Roman", size: 23))
                        .padding(.top, 60)
                        .onAppear {
                            subscription = usuario.newsletter
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
    Perfil()
}
