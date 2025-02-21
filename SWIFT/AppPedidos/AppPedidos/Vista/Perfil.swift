//
//  Perfil.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

import SwiftUI

struct Perfil: View {
    
    @State var username: String = ""
    @State var email: String = ""
    @State var postalCode: Int? = nil
    @State var subscription: Bool = false
    
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
                        
                    Text("username")
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

                        TextField("Postal Code", text: $email)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .font(.custom("Times New Roman", size: 25))
                    }
                    
                    Toggle("Newsletter subscription:",isOn: $subscription)
                        .font(.custom("Times New Roman", size: 23))
                        .padding(.top, 60)
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
