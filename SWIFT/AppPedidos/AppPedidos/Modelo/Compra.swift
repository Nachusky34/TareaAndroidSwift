//
//  Compra.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

struct Compra: Codable, Identifiable {
    let id: String
    let fecha: String
    let hora: String
    let cantidad: Int
}
