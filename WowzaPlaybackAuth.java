package com.wowza.test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MediaServerPlaybackApi {
    protected String server = "123.123.123.123";
    protected String server_port = "1935";
    protected String application = "testlive";
    protected final String instance = "_definst_";
    protected String stream = "testlive.stream";
    protected String secret_key = "qw32c8w1a6s51asd3";
    protected String prefix_param = "prefix_";
    protected String endtime = "1603776531";
    protected String starttime = "0";
    protected final String endtime_param = "endtime";
    protected final String starttime_param = "starttime";
    protected final String hash_param = "hash";
    protected final String hls_metafile_name = "playlist.m3u8";

    public static void main(String[] args) {
        MediaServerPlaybackApi api = new MediaServerPlaybackApi(
                "123.123.123.123", "80",
                "testlive", "testlive.stream",
                "qw32c8w1a6s51asd3", "prefix_",
                "0", "1603776531");
        String hls_url = api.getAuthHlsUrl();
        System.out.println(hls_url);
    }

    public MediaServerPlaybackApi(String server, String server_port,
                          String application, String stream,
                          String secret_key, String prefix_param,
                          String utc_starttime, String utc_endtime) {
        this.server = server;
        this.server_port = server_port;
        this.application = application;
        this.stream = stream;
        this.secret_key = secret_key;
        this.prefix_param = prefix_param;
        this.starttime = utc_starttime;
        this.endtime = utc_endtime;
    }

    public String getAuthHlsUrl() {
        try {
            return "http://"+
                    this.server+":"+
                    this.server_port+"/"+
                    this.application+"/"+
                    this.instance+"/"+
                    this.stream+"/"+
                    this.hls_metafile_name+"?"+
                    this.prefix_param+this.starttime_param+"="+this.starttime+"&"+
                    this.prefix_param+this.endtime_param+"="+this.endtime+"&"+
                    this.prefix_param+this.hash_param+"="+makeHash(makeHashURL());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String makeHashURL() {
        String[] params = {this.secret_key,
                this.prefix_param+this.endtime_param+"="+this.endtime,
                this.prefix_param+this.starttime_param+"="+this.starttime};
        Arrays.sort(params);
        return this.application+"/"+this.instance+"/"+this.stream+"?"+params[0]+"&"+params[1]+"&"+params[2];
    }

    private String makeHash(String hash_params) throws NoSuchAlgorithmException {
        return hashReplacer(base64(sha256(hash_params)));
    }

    private byte[] sha256(String msg)  throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        //return CryptoUtilsbyteToHexString(md.digest());
        return md.digest();
    }

    private String base64(byte[] msg) {
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(msg);
    }

    private String hashReplacer(String msg) {
        return msg.replace('+','-').replace('/','_');
    }
}
