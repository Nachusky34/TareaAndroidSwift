//
//  DatosUsuarios.swift
//  AppPedidos
//
//  Created by Usuario invitado on 25/2/25.
//

import SwiftUI

// Definimos un objeto que almacenará los datos del usuario y lo haremos observable
class UserData: ObservableObject {
    @Published var usuario: Usuario?
}
