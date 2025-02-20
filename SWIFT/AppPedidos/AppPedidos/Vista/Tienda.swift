//
//  Tienda.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

import SwiftUI

struct Tienda: View {
    
    @State var username: String = ""
    @State var email: String = ""
    @State var postalCode: Int? = nil
    @State var subscription: Bool = false
    
    var body: some View {
        VStack {
            Button(action: {
                // aqui nos lleva a la pestaña de CARRITO
            }) {
                Image(systemName: "cart")
                    .padding(.leading, 280)
                    .font(.system(size: 40))
                    .foregroundColor(.black)
            }
            
            Image(.ordenador1)
                .padding(.top, -40)
            
            
            
            VStack(alignment: .leading) {
                Text("ORDENADOR 1")
                    .font(.custom("Times New Roman", size: 32))
                    .fontWeight(.bold)
            }
            .padding(.horizontal, 25)
            
            Spacer()
        }
        .padding()
    }
}


#Preview {
    Tienda()
}
