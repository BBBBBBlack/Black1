package work;

public class InsufficientBalanceException extends RuntimeException{
    static final long serialVersionUID = -703489719074576693L;
    public InsufficientBalanceException(){

    }
    public InsufficientBalanceException(String b){
        super(b);
    }
}
