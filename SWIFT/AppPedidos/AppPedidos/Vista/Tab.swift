//
//  Tab.swift
//  AppPedidos
//
//  Created by Mario Seoane on 20/2/25.
//

import SwiftUI

// La vista principal de la aplicación que contiene el TabView.
struct Tab: View {
    var body: some View {
        TabView {
            Perfil()
                .tabItem {
                    Label("Perfil", systemImage: "person")
                }
                .tag(1) // tag se utiliza para identificar cada tab de forma única
            
            Tienda()
                .tabItem {
                    Label("Tienda", systemImage: "house")
                }
                .tag(2)
            
            Perfil()
                .tabItem {
                    Label("Historial", systemImage: "clock.arrow.trianglehead.counterclockwise.rotate.90")
                }
                .tag(3)
        }
        .accentColor(Color(red: 85/255, green: 183/255, blue: 232/255))
    }
}

#Preview {
    Tab()
}
