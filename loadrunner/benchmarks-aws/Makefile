OUT_ROOT=/user/hadoop/out
ARTIFACTS_PATH=out/artifacts

JAR_REG_NAME=${ARTIFACTS_PATH}/test_reg.jar
JAR_PLU_NAME=${ARTIFACTS_PATH}/test_plu.jar

LIB_PATH=lib-min
PLUGIN_JAR_NAME=${LIB_PATH}/build-plugin.jar

INPUT_PATH=input/small/
OUTPUT_PATH=${OUT_ROOT}/run_results/
LOGR_PATH=s3://aws-logs-462631940861-us-east-2/plugin-benchmark/

NUM_ITER=10000
BUFFER_SIZE=100

# Path to spark-submit executable
SPARK_SUBMIT = "spark-submit"
SCALAC = "scalac"


PEM_FILE=~/ssh1.pem
AWS_HOST=ec2-18-216-198-137.us-east-2.compute.amazonaws.com
YARN_IP=172.31.5.165

all: run

run:  run_diff

run_reg:
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master yarn --deploy-mode cluster --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_REG_NAME} \
    	${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_reg ${NUM_ITER} ${BUFFER_SIZE} ${AWS_HOST}

run_plu:
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master yarn --deploy-mode cluster --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_PLU_NAME}  \
          ${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_plugin ${NUM_ITER} ${BUFFER_SIZE} ${AWS_HOST}

run_diff: run_reg run_plu
	@echo "Running diff to validate outputs"
	@for f in $$(ls ${OUTPUT_PATH});do \
		diff -a -q ${OUTPUT_PATH}$$f/run_plugin/part-00000 ${OUTPUT_PATH}$$f/run_reg/part-00000; \
	done
	@echo "End diff to validate outputs"


setup: clean
	hadoop fs -mkdir -p ${INPUT_PATH}
	hadoop fs -copyFromLocal ${INPUT_PATH} input/

clean:
	-hadoop fs -rm -rf ${INPUT_PATH}
	rm -rf ${OUT_ROOT}/run_results/

push:
	scp -i ${PEM_FILE} -r $$(pwd) hadoop@${AWS_HOST}:/mnt/


pull:
	scp -i ${PEM_FILE} -r hadoop@${AWS_HOST}:/mnt/benchmarks-aws $$(pwd)/tmp

ssh:
	ssh -i ${PEM_FILE} hadoop@${AWS_HOST}
