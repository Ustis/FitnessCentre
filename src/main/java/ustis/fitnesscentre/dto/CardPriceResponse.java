package ustis.fitnesscentre.dto;

public class CardPriceResponse {
    private String cardPrice;
    private String balance;

    public CardPriceResponse() {
    }

    public CardPriceResponse(String cardPrice, String balance) {
        this.cardPrice = cardPrice;
        this.balance = balance;
    }

    public String getCardPrice() {
        return cardPrice;
    }

    public void setCardPrice(String cardPrice) {
        this.cardPrice = cardPrice;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
