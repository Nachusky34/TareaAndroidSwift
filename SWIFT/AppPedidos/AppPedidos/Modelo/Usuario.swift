//
//  Usuario.swift
//  AppPedidos
//
//  Created by Usuario invitado on 20/2/25.
//

import Foundation

struct Usuario: Decodable {
    let id: Int
    let username: String
    let password: String
    let email: String
    let postalcode: String
    let newsletter: Bool
    let foto: String
}
