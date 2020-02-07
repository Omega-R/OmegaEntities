//
//  TextExamplesView.swift
//  OmegaEntitiesExample
//
//  Created by Nikita Ivanov on 06.02.2020.
//  Copyright © 2020 Omega-R. All rights reserved.
//

import SwiftUI
import library


struct TextExamplesView: View {
    
    private static let kExamples = [
        Example(id: 0,
                name: StringOmegaText(value: "Text from String"),
                example: StringOmegaText(value: "String Example"))
    ]
    
    
    var body: some View {
        List(TextExamplesView.kExamples) { (example: Example) in
            HStack {
                Text(example.name.getString()!)
                Spacer()
                Divider()
                Spacer()
                Text(example.example.getString()!)
            }
        }
        .navigationBarTitle(Text("Text Examples"), displayMode: .inline)
    }
}

struct TextExamplesView_Previews: PreviewProvider {
    static var previews: some View {
        TextExamplesView()
    }
}

private struct Example: Identifiable {
    var id: Int
    var name: OmegaText
    var example: OmegaText
}

