//
//  Usuario.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

struct Usuario: Codable, Identifiable {
    let id: Int
    let nombre: String
    let pwd: String
    let email: String
    let postalCode: Int
    let foto: String
}
