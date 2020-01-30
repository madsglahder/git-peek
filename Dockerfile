FROM clojure:lein
COPY . .
RUN lein deps
RUN lein uberjar
EXPOSE 3000
CMD ["java", "-jar", "target/uberjar/git-peek-0.1.0-SNAPSHOT-standalone.jar"]