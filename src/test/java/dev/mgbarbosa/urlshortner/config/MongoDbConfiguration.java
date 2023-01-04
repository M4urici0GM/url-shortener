//
//import com.mongodb.MongoClientSettings;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.mongo.MongoProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
//@Configuration
//public class TestMongoConfig {
//
//  @Autowired
//  private MongoProperties properties;
//
//  @Autowired(required = false)
//  private MongoClientSettings options;
//
//  @Bean(destroyMethod = "close")
//  public Mongod mongo(MongodProcess mongodProcess) throws IOException {
//    Net net = mongodProcess.getConfig().net();
//    properties.setHost(net.getServerAddress().getHostName());
//    properties.setPort(net.getPort());
//    return properties.createMongoClient(this.options);
//  }
//
//  @Bean(destroyMethod = "stop")
//  public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
//    return mongodExecutable.start();
//  }
//
//  @Bean(destroyMethod = "stop")
//  public MongodExecutable mongodExecutable(MongodStarter mongodStarter, IMongodConfig iMongodConfig) throws IOException {
//    return mongodStarter.prepare(iMongodConfig);
//  }
//
//  @Bean
//  public IMongodConfig mongodConfig() throws IOException {
//    return new MongodConfigBuilder().version(Version.Main.PRODUCTION).build();
//  }
//
//  @Bean
//  public MongodStarter mongodStarter() {
//    return MongodStarter.getDefaultInstance();
//  }
//
//}