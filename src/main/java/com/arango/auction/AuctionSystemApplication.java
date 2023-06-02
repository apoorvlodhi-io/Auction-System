package com.arango.auction;

import com.arangodb.ArangoDB;
import com.arangodb.entity.ArangoDBVersion;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.AbstractArangoConfiguration;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AuctionSystemApplication {
	@Autowired
	private ArangoOperations arangoOperations;
	@PostConstruct
	void init() {
		ArangoDBVersion version = arangoOperations.getVersion();
		System.out.println(version.getVersion());
	}

	public static void main(String[] args) {
		SpringApplication.run(AuctionSystemApplication.class, args);
	}

}

@Configuration
@EnableArangoRepositories
class ArangoConfig extends AbstractArangoConfiguration {
	@Override
	public ArangoDB.Builder arango() {
		// Base64 encoded CA certificate
		String encodedCA = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURHRENDQWdDZ0F3SUJBZ0lRSENmdXoyTFc1OG1YajhodEh3NHhqekFOQmdrcWhraUc5dzBCQVFzRkFEQW0KTVJFd0R3WURWUVFLRXdoQmNtRnVaMjlFUWpFUk1BOEdBMVVFQXhNSVFYSmhibWR2UkVJd0hoY05Nak13TlRNeApNRFV6TnpVMFdoY05Namd3TlRJNU1EVXpOelUwV2pBbU1SRXdEd1lEVlFRS0V3aEJjbUZ1WjI5RVFqRVJNQThHCkExVUVBeE1JUVhKaGJtZHZSRUl3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRRFcKUEFkektCTU9MSUJaT2lDR3M2TytWTFBtWlpScm5nZlFjbkhzZVdTUmxDSy9Wb05teTdFdGlEUG5SZ3d5YlVHQgp6TmJHeFBxTUpwRGtNenhlRlg0dUVndmRCU1BXSjVwSjdUV1JHK1A3QVFVeGx4YktqV0laWEE4MXlNRGtVS3gxClBxeFZoUFlNSC9UeVRGZUk5OEtiVG5lbkdZeVhVSHllRkx1RGdVNmdTSHRXZ251MVpUZUpZSjI2eUptZXVXYXoKNzBJUHU1R205T3VKdit6MUVjaFY2aVVXaHdIQ01lLzNQYzZqN2c3K3BqakI2RkRLRk5Kc0pIa29qZVpiZjFONgppTUdWSlphVmlMWVk5d3JMVU9lTDlVNUdUc0J4RjNXcTd1bi9VVURTN3dOMFFoL0NhQ2dkTmZMbVdRQ1RQR29aClRLL3VIeGdnMCtLZTFiWTE1blVsQWdNQkFBR2pRakJBTUE0R0ExVWREd0VCL3dRRUF3SUNwREFQQmdOVkhSTUIKQWY4RUJUQURBUUgvTUIwR0ExVWREZ1FXQkJTVWxGY3Z1OFFDOWF2WjlZdXdKaDUyaHI2bDVEQU5CZ2txaGtpRwo5dzBCQVFzRkFBT0NBUUVBVGp1dUNlMjhsSTFXWXlCZExjNjZ0SkRpY2RReU9UeUV2M2hhdFlPc3AyRFpzRXllCmZwa2daMHF4Zm5Od21zUlIvejlxVFQrRnBPOFVzNDdmNW5iV3N4Y0JROWFURTdmc0kzK29IZzlqaHh4R1hteG0KNm0rMGd6dW52U2xESW8wM3JTL24wZVBHaXVValpjbkNSMjdLZkYvc1BSV2ZyZEZJQXoyZUtqOHJ0Vk5MTFFCdgpEenFsSnloTitwblNRK0psZHRBdlJYWFBCazltR2pSVmZsaVdxNTNzNHNyMFJ5RlgrYjNsWkxweXJ5VGxnMzUyCmhrdG5wcXQ1WmZ2TnR3Qy9OcC9ZVjRSNjJJQzQzajdManVQem1leVNvdjZYeEFRV3lzY1FoY1RjNVNrbWx5SlkKZklBeFBqV2YzMG13SXBRbTZuNEhyYzlQcFhuM2pJMGNjb1JPcmc9PQotLS0tLUVORCBDRVJUSUZJQ0FURS0tLS0tCg==";
		InputStream is = new java.io.ByteArrayInputStream(Base64.getDecoder().decode(encodedCA));
		SSLContext sslContext;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate caCert = (X509Certificate) cf.generateCertificate(is);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null);
			ks.setCertificateEntry("caCert", caCert);
			tmf.init(ks);
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tmf.getTrustManagers(), null);
		} catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException |
				 KeyManagementException e) {
			throw new RuntimeException(e);
		}
		return new ArangoDB.Builder()
				.useSsl(true)
				.host("42da8e4b7d3d.arangodb.cloud", 18529)
				.user("root")
				.password("jgymn8mewIhGQLFvXPi9")
				.sslContext(sslContext);
	}
	@Override
	public String database() {
		return "mydb";
	}
}