package core;

public enum statusCode {

SUCCESS(200,"success passed"),
 CREATED(201,"New Entity Created"),
UPDATED(204,"updated");

    public final int code;
    public final String msg;
    statusCode(int code,String msg)
    {
        this.code=code;
        this.msg=msg;
    }
}
