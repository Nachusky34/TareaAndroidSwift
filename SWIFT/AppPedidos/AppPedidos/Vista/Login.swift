//
//  Login.swift
//  AppPedidos
//
//  Created by Mario Seoane on 13/2/25.
//

import SwiftUI

struct Login: View {
    
    @State var username: String = ""
    @State var password: String = ""
    
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
                print("Sign In tapped")
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
    ContentView()
}
