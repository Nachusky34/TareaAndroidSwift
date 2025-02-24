//
//  Login.swift
//  AppPedidos
//
//  Created by Mario Seoane on 13/2/25.
//

import SwiftUI

struct Login: View {
    
    let usuarios: [Usuario] = CargarDatosUsuario()
    
    @State var username: String = ""
    @State var password: String = ""
    @State var mostrarAlerta: Bool = false
    @State var isAuthenticated: Bool = false
    
    var body: some View {
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
                    .padding(.bottom, 0)
                    
                VStack(alignment: .leading) {
                    TextField("Username", text: $username)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                        .font(.custom("Times New Roman", size: 25))
                        .padding(.bottom, 20)
                        .padding(.top, 30)
                                        
                    SecureField("Password", text: $password)
                        .font(.custom("Times New Roman", size: 25))
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                }
            }
                .padding(.horizontal, 45)
                .padding(.bottom, 100)
                        
            
           
            Button(action: {
                for usuario in usuarios {
                    if (usuario.nombre == username && usuario.pwd == password) {
                        mostrarAlerta = false
                        isAuthenticated = true
                    } else {
                        mostrarAlerta = true
                    }
                }
                
                    
                
            }) {
                Text("SIGN IN")
                    .font(.custom("Times New Roman", size: 18))
                    .frame(width: 90, height: 20)
                    .padding()
                    .background(Color.blue.opacity(0.2))
                    .foregroundColor(.blue)
                    .cornerRadius(10)
                    .padding(.horizontal, 100)
                    .padding(.trailing, 160)
                    .padding(.bottom, -60 )
                }
            .alert(isPresented: $mostrarAlerta) {
                                Alert(
                                    title: Text("Error"),
                                    message: Text("Username or password incorrect"),
                                    dismissButton: .default(Text("OK"))
                                )
                            }
                        
                        // Navegar a Tab() cuando el login es exitoso
                        NavigationLink(destination: Tab(), isActive: $isAuthenticated) {
                            EmptyView()
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

#Preview {
    Login()
}
