FROM adoptopenjdk/openjdk8:jdk8u292-b10

RUN apt-get update && \
    apt-get install -y zip unzip && \
    rm -rf /var/lib/apt/list/*


SHELL ["/bin/bash", "-c"]

RUN groupadd -g 1000 kayshu
RUN useradd --uid 1000 --gid 1000 --system --create-home --shell /bin/bash --no-log-init kayshu
USER kayshu

RUN curl -s "https://get.sdkman.io" | bash && \
    source "/home/kayshu/.sdkman/bin/sdkman-init.sh" && \
    sdk install gradle 3.3

WORKDIR /zip-repository
