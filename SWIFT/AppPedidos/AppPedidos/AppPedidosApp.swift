//
//  AppPedidosApp.swift
//  AppPedidos
//
//  Created by Usuario invitado on 13/2/25.
//

import SwiftUI

@main
struct AppPedidosApp: App {
    
    @StateObject var usuario = UserData()
    
    
    var body: some Scene {
        WindowGroup {
            Login()
                .environmentObject(usuario)
        }
    }
}
