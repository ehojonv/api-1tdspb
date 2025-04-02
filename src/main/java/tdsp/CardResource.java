package tdsp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response getCardById(@PathParam("id") int id) {
        var card = cards.stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);

        if (card == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(card).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCard(Card card) {
        cards.add(card);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCard(@PathParam("id") int id, Card card) {
        var cardToUpdate = cards.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (cardToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        cardToUpdate.setName(card.getName());
        cardToUpdate.setTxt(card.getTxt());
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCardById(@PathParam("id") int id) {
        cards.removeIf(c -> c.getId() == id);
        return Response.status(Response.Status.OK).build();
    }


    public int PAGE_AMOUNT = 2;

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(
            @QueryParam("name") String name,
            @QueryParam("text") String text,
            @QueryParam("page") int page,
            @QueryParam("order") String orderby) {

        var cardsSearched = cards.stream()
                .filter(c ->
                        (name == null || c.getName().contains(name) &&
                        (text == null || c.getTxt().contains(text))))
                .sorted(
                        orderby != null && orderby.equals("name") ?
                                (a, b) -> a.getName().compareToIgnoreCase(b.getName()) :
                                (a, b) -> a.getTxt().compareToIgnoreCase(b.getTxt()))
                .toList();

        var start = (page - 1) * cardsSearched.size();
        var end = Math.min(start + PAGE_AMOUNT, cardsSearched.size());

        if (page > 0) {
            if (start >= cardsSearched.size()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(cardsSearched.subList(start, end)).build();
        } else {
            return Response.ok(cardsSearched.subList(0, end)).build();
        }
    }


}
