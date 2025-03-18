//
//  Tienda.swift
//  AppPedidos
//
//  Created by Mario Seoane on 25/2/25.
//
import SwiftUI

struct Historial: View {
    @State var ordenadores: [Ordenador] = CargarDatosOrdenador()
    @State var compras: [Compra] = CargarDatosCompra()
    
    @Binding var usuario: Usuario
    
    @State private var mostrarCarrito = false
    
    // Filtrar compras con "comprado = true" y pertenecientes al usuario
    var comprasFiltradas: [Compra] {
        compras.filter { compra in
            compra.comprado && compra.idUsuario == usuario.id && ordenadores.contains { $0.id == compra.idProducto }
        }
    }
    
    var body: some View {
        VStack {
            HStack {
                Text("History")
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
            
            if comprasFiltradas.isEmpty {
                VStack {
                    Image(systemName: "clock.arrow.circlepath")
                        .resizable()
                        .frame(width: 80, height: 80)
                        .foregroundColor(.gray)
                        .padding(.bottom, 10)
                    
                    Text("There are no purchases in history.")
                        .font(.custom("Times New Roman", size: 25))
                        .foregroundColor(.gray)
                }
                .frame(maxHeight: .infinity)
            } else {
                NavigationView {
                    List(comprasFiltradas) { compra in
                        if let ordenador = ordenadores.first(where: { $0.id == compra.idProducto }) {
                            HStack(spacing: 10) {
                                VStack {
                                    Image(ordenador.img)
                                        .resizable()
                                        .frame(width: 100, height: 100)
                                        .cornerRadius(8)
                                    
                                    Text(compra.fecha)
                                        .font(.custom("Times New Roman", size: 14))
                                    
                                    Text(compra.hora)
                                        .font(.custom("Times New Roman", size: 14))
                                }
                                VStack(alignment: .leading) {
                                    Text(ordenador.nombre)
                                        .font(.custom("Times New Roman", size: 20))
                                        .bold()
                                    
                                    Text("Amount: \(compra.cantidad)")
                                        .font(.custom("Times New Roman", size: 15))
                                        .padding(.top, 15)
                                    
                                    Text(String(format: "%.2f$", ordenador.precio * Double(compra.cantidad)))
                                        .font(.title2)
                                        .foregroundColor(Color(red: 85/255, green: 183/255, blue: 232/255))
                                        .padding(.top, 20)
                                        .padding(.leading, 10)
                                }
                                Spacer()
                            }
                            .padding(8)
                        }
                    }
                }
            }
        }
        .onAppear {
            // Recargar las compras desde el archivo JSON cuando la vista aparezca
            compras = CargarDatosCompra()
        }
        .fullScreenCover(isPresented: $mostrarCarrito) {
            Carrito(usuario: usuario)
        }
    }
}

#Preview {
    let usuarioFicticio = Usuario(id: 12, username: "marioseoane", password: "12", email: "marioseoane@marioseoane.marioseoane", postalcode: "12345", newsletter: true, foto: "marioseoane")
    Historial(usuario: .constant(usuarioFicticio))
}

