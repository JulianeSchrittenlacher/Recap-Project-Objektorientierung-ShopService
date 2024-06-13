public class IdNotFoundException  extends Exception{
    public IdNotFoundException(String id) {
        super("Id not found: " + id);
    }
}