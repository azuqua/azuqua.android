package sasidhar.azuqua.com.azuquaapitest;

/**
 * Created by BALASASiDHAR on 26-May-15.
 */
public class APIList {

    int _id;
    String _name;
    String _protocol;
    String _host;
    int _port;

    public APIList(){

    }


    public APIList(int id, String name, String protocol, String host, int port){

        this._id = id;
        this._name = name;
        this._protocol = protocol;
        this._host = host;
        this._port = port;

    }

    public APIList(String name, String protocol, String host, int port){

        this._name = name;
        this._protocol = protocol;
        this._host = host;
        this._port = port;

    }

    public int getID() {
        return _id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getProtocol() {
        return _protocol;
    }

    public void setProtocol(String protocol) {
        this._protocol = protocol;
    }

    public String getHost() {
        return _host;
    }

    public void setHost(String host) {
        this._host = host;
    }

    public int getPort() {
        return _port;
    }

    public void setPort(int port) {
        this._port = port;
    }

    public String getAPI(){
        return this._protocol+"://"+this._host+":"+this._port;
    }
}
