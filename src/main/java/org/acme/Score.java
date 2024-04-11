package org.acme;

import org.eclipse.microprofile.graphql.Type;

@Type("Score")
public record Score(Long hard, Long medium, Long soft) {
}
