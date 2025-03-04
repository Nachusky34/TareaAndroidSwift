//
//  Compra.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

struct Compra: Codable {
    let idUsuario : Int
    let idProducto : Int
    let fecha: String
    let hora: String
    let cantidad: Int
    let comprado : Bool
}
