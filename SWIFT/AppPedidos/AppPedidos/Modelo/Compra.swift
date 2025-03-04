//
//  Compra.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

struct Compra: Codable, Identifiable {
    let id : String
    var idUsuario: Int
    var idProducto: Int
    var fecha: String
    var hora: String
    var cantidad: Int
    var comprado: Bool
}
