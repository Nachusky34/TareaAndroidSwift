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
    
    
    init(id: Int, username: String, password: String, email: String, postalcode: String, newsletter: Bool, foto: String) {
        self.id = id
        self.username = username
        self.password = password
        self.email = email
        self.postalcode = postalcode
        self.newsletter = newsletter
        self.foto = foto
    }
}
