package tdsp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/card")
public class CardResource {
    static List<Card> cards =
            new ArrayList<>(List.of(
                    new Card(1, "Card 1", "Text 1"),
                    new Card(2, "Card 2", "Text 2"),
                    new Card(3, "Card 3", "Text 3")
            ));

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Card> getCards() {
        return cards;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Card getCardById(@PathParam("id") int id) {
        return cards.stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addCard(Card card) {
        cards.add(card);
    }


}
