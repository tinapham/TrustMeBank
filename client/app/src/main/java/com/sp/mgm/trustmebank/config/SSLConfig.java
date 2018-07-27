package com.sp.mgm.trustmebank.config;

import android.content.res.Resources;
import android.util.Log;

import com.sp.mgm.trustmebank.R;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.net.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLConfig {

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    public static void SSLPinning(Resources resources) {

        try {

            KeyStore trusted = KeyStore.getInstance("BKS");
            InputStream store = resources.openRawResource(R.raw.trustmebank);
            trusted.load(store, "trustmebank".toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trusted);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Set verifier = Allow all hostname
            HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

            HttpsURLConnection.setDefaultSSLSocketFactory(
                    sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void trustPK() {
//        try {
//            // Load CAs from an InputStream
//            // (could be from a resource or ByteArrayInputStream or ...)
//            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            // From https://trustmebank.com/ca
//            InputStream caInput = new BufferedInputStream(new FileInputStream("ca.bks"));
//            Certificate ca;
//            try {
//                ca = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
//            } finally {
//                caInput.close();
//            }
//
//            // Create a KeyStore containing our trusted CAs
//            String keyStoreType = KeyStore.getDefaultType();
//            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("ca", ca);
//
//            // Create a TrustManager that trusts the CAs in our KeyStore
//            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//            tmf.init(keyStore);
//
//            // Create an SSLContext that uses our TrustManager
//            SSLContext context = SSLContext.getInstance("TLS");
//            context.init(null, tmf.getTrustManagers(), null);
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(
//                    context.getSocketFactory());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
