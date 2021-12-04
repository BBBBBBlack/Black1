package work;

public class CatNotFoundException extends RuntimeException{
    static final long serialVersionUID = -703489719074576693L;
    public CatNotFoundException(){

    }
    public CatNotFoundException(String a){
        super(a);
    }
}
