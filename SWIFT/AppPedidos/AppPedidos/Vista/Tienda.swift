//
//  Tienda.swift
//  AppPedidos
//
//  Created by Mario Seoane on 25/2/25.
//

import SwiftUI

struct Tienda: View {
    
    @State private var ordenadores: [Ordenador] = CargarDatosOrdenador()
    
    @State private var ordenadorSeleccionado: Ordenador?
    
    @Binding var usuario: Usuario
    
    @State private var mostrarCarrito = false

    var body: some View {
        VStack {
            HStack {
                Text("Tienda")
                    .font(.custom("Times New Roman", size: 40))
                    .fontWeight(.bold)
                    .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                
                Spacer()
                
                Button(action: {
                    mostrarCarrito = true
                }) {
                    Image(systemName: "cart")
                        .font(.system(size: 40))
                        .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                }
            }
            .padding(.horizontal, 30)
            .padding(.top, 20)
            
            NavigationView {
                List(ordenadores) { ordenador in
                    Button(action: {
                        ordenadorSeleccionado = ordenador
                    }) {
                        HStack {
                            Image(ordenador.img)
                                .resizable()
                                .frame(width: 100, height: 100)
                                .cornerRadius(8)
                            
                            VStack(alignment: .leading) {
                                Text(ordenador.nombre)
                                    .font(.custom("Times New Roman", size: 30))
                                    .foregroundColor(.black)
                                
                                Text(String(format: "%.2f$", ordenador.precio))
                                    .font(.custom("Times New Roman", size: 25))
                                    .foregroundColor(.gray)
                                    .padding()
                                    .padding(.leading, 60)
                                
                            }
                            
                            Spacer()
                        }
                        .padding(8)
                    }
                }
            }
        }
        
    
        .sheet(item: $ordenadorSeleccionado) { ordenador in
            VistaProducto(ordenador : ordenador, usuario: usuario)
        }
        
        .fullScreenCover(isPresented: $mostrarCarrito) {
            Carrito(usuario: usuario)
        }
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Tienda(usuario: .constant(usuarioFicticio))
}
