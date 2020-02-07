//
//  ContentView.swift
//  OmegaEntitiesExample
//
//  Created by Nikita Ivanov on 06.02.2020.
//  Copyright © 2020 Omega-R. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    fileprivate let content = [
        Row(id: 0, name: "Text"),
        Row(id: 1, name: "Image")
    ]
    
    var body: some View {
        NavigationView {
            List(content) { row in
                NavigationLink(destination: TextExamplesView()) {
                    HStack {
                        Spacer()
                        Text(row.name)
                        Spacer()
                    }
                }
            }
            .navigationBarTitle(Text("Examples"))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

fileprivate struct Row: Identifiable {
    var id: Int
    let name: String
}
