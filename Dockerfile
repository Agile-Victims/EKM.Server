# Java 17 tabanlı image
FROM eclipse-temurin:17-jdk-jammy AS build

# Uygulama dosyalarının kopyalanacağı klasör
WORKDIR /app

# Maven/Gradle dosyalarını önce kopyala (dependency cache için)
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src

# Maven ile proje build et (eğer Gradle kullanıyorsan burayı değiştiririz)
RUN ./mvnw clean package -DskipTests

# Yeni aşama: sadece jar dosyasını çalıştıracak
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Build aşamasından jar dosyasını al
COPY --from=build /app/target/*.jar app.jar

# Portu aç
EXPOSE 8080

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
