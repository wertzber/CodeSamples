package subscription2.impl;

import com.liveperson.api.ReqBody;
import com.liveperson.api.RespBody;
import com.liveperson.api.server.RequestMsg;
import com.liveperson.api.server.ResponseMsg;

import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: ofirp
 * Date: 2/2/2016
 * Time: 4:29 PM
 */
class SubscribeIntegers implements RequestMsg {
    public Parity parity;

    public SubscribeIntegers(Parity parity) {
        this.parity = parity;
    }

    @Override
    public ReqBody getBody() {
        return parity;
    }

    @Override
    public ResponseMsg response(Response.Status code, RespBody respBody) {
        return new ResponseMsg() {
            @Override
            public RespBody getBody() {
                return respBody;
            }

            @Override
            public String getReqId() {
                return "1";
            }

            @Override
            public Response.Status getCode() {
                return code;
            }
        };
    }
}
