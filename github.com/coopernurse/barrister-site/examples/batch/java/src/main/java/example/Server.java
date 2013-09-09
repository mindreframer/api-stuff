package example;

import com.bitmechanic.barrister.RpcException;

public class Server implements Echo {

    public String echo(String s) throws RpcException {
        if (s.equals("err")) {
            throw new RpcException(99, "Error!");
        }
        else {
            return s;
        }
    }

}