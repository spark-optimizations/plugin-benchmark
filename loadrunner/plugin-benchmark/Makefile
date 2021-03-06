OUT_ROOT=out
ARTIFACTS_PATH=${OUT_ROOT}/artifacts

JAR_REG_NAME=${ARTIFACTS_PATH}/test_reg.jar
JAR_PLU_NAME=${ARTIFACTS_PATH}/test_plu.jar

MAIN_BENCHMARK_PATH=../../
INPUT_PATH=input/tiny/
OUTPUT_PATH=${OUT_ROOT}/run_results/$(date +%s%)/
LOGR_PATH=results/stats/
METRICS_FILE=results/com_timing.csv

NUM_ITER=1000
BUFFER_SIZE=100

# Path to spark-submit executable
SPARK_SUBMIT = "spark-submit"
SCALAC = "scalac"

all: build run

run: run_reg run_plu run_diff

run_build:
	for i in $$(seq 1 ${NUM_ITER}); do \
		make build_reg_time; \
	done;

build: setup build_reg_time
	cp ${MAIN_BENCHMARK_PATH}${ARTIFACTS_PATH} ${ARTIFACTS_PATH}

build_reg_time:
	cd ${MAIN_BENCHMARK_PATH} && (time -p make build_reg) 2>&1 | grep "real" | sed 's/^/com_reg /' >> ${METRICS_FILE}
	cd ${MAIN_BENCHMARK_PATH} && (time -p make build_plu) 2>&1 | grep "real" | sed 's/^/com_plugin /' >> ${METRICS_FILE}


run_reg:
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master local --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_REG_NAME} \
    	${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_reg ${NUM_ITER} ${BUFFER_SIZE}

run_plu:
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master local --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_PLU_NAME}  \
          ${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_plugin ${NUM_ITER} ${BUFFER_SIZE}

run_diff:
	@echo "Running diff to validate outputs"
	@for f in $$(ls ${OUTPUT_PATH});do \
		-diff -a -q ${OUTPUT_PATH}$$f/run_plugin/part-00000 ${OUTPUT_PATH}$$f/run_reg/part-00000; \
	done
	@echo "End diff to validate outputs"

setup:

clean:
	@rm -rf ${OUT_ROOT}/run_results/

consolidate:
	@for f in $$(ls ${LOGR_PATH});do \
		cat ${LOGR_PATH}$$f/part-00000 >> ${LOGR_PATH}../run_timing.csv; \
	done;