package example;

import com.bitmechanic.barrister.HttpTransport;
import com.bitmechanic.barrister.RpcException;
import com.bitmechanic.barrister.RpcResponse;
import com.bitmechanic.barrister.Batch;
import java.util.List;

public class Client {

    public static void main(String argv[]) throws Exception {
        HttpTransport trans = new HttpTransport("http://127.0.0.1:8080/example/");
        EchoClient client = new EchoClient(trans);
        
        System.out.println("hello");
        try {
            client.echo("err");
        }
        catch (RpcException e) {
            System.out.println("err.code=" + e.getCode());
        }

        Batch batch = new Batch(trans);
        EchoClient batchEcho = new EchoClient(batch);
        batchEcho.echo("batch 0");
        batchEcho.echo("batch 1");
        batchEcho.echo("err");
        batchEcho.echo("batch 2");
        batchEcho.echo("batch 3");

        List<RpcResponse> result = batch.send();
        for (RpcResponse resp : result) {
            if (resp.getError() != null) {
                System.out.println("err.code=" + resp.getError().getCode());
            }
            else {
                System.out.println(resp.getResult());
            }
        }
    }

}