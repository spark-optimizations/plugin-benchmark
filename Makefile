OUT_ROOT=out
CLASSES_PATH=${OUT_ROOT}/classes
ARTIFACTS_PATH=${OUT_ROOT}/artifacts

JAR_REG_NAME=${ARTIFACTS_PATH}/test_reg.jar
JAR_PLU_NAME=${ARTIFACTS_PATH}/test_plu.jar

LIB_PATH=lib-min
PLUGIN_JAR_NAME=${LIB_PATH}/build-plugin.jar

INPUT_PATH=input/tiny/
OUTPUT_PATH=${OUT_ROOT}/run_results/
LOGR_PATH=results/stats/
METRICS_FILE=results/com_timing.csv

NUM_ITER=100000
BUFFER_SIZE=1000

# Path to spark-submit executable
SPARK_SUBMIT = "spark-submit"
SCALAC = "scalac"

all: run

run_build:
	for i in $$(seq 1 ${NUM_ITER}); do \
		make build_reg_time; \
	done;

build_reg_time:
	(time -p $(SCALAC) -d ${CLASSES_PATH} \
		-cp "./${LIB_PATH}/*" \
		src/main/scala/org/so/benchmark/util/*.scala \
		src/main/scala/org/so/benchmark/plugin/*.scala \
		 ) 2>&1 | grep "real" | sed 's/^/com_reg /' >> ${METRICS_FILE}
	(time  -p $(SCALAC) -d ${CLASSES_PATH} \
    		-cp "./${LIB_PATH}/*" \
    		-Xplugin:${PLUGIN_JAR_NAME} \
    		src/main/scala/org/so/benchmark/util/*.scala \
    		src/main/scala/org/so/benchmark/plugin/*.scala \
             ) 2>&1 | grep "real" | sed 's/^/com_plugin /' >> ${METRICS_FILE}

build: setup build_reg build_plu

build_reg:
	$(SCALAC) -d ${CLASSES_PATH} \
        		-cp "./${LIB_PATH}/*" \
        		src/main/scala/org/so/benchmark/util/*.scala \
        		src/main/scala/org/so/benchmark/plugin/*.scala
	jar cf ${JAR_REG_NAME} \
    	-C ${CLASSES_PATH} .
build_plu:
	$(SCALAC) -d ${CLASSES_PATH} \
        		-cp "./${LIB_PATH}/*" \
    			-Xplugin:${PLUGIN_JAR_NAME} \
        		src/main/scala/org/so/benchmark/util/*.scala \
        		src/main/scala/org/so/benchmark/plugin/*.scala
	jar cf ${JAR_PLU_NAME} \
    	-C ${CLASSES_PATH} .

run_reg: build_reg
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master local --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_REG_NAME} \
    	${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_reg ${NUM_ITER} ${BUFFER_SIZE}

run_plu: build_plu
	$(SPARK_SUBMIT) \
    	--packages net.liftweb:lift-json_2.11:3.1.1 \
	 	--master local --driver-memory 5g \
    	--class org.so.benchmark.plugin.Main ${JAR_PLU_NAME}  \
          ${INPUT_PATH} ${OUTPUT_PATH} ${LOGR_PATH} run_plugin ${NUM_ITER} ${BUFFER_SIZE}

run_diff: run_reg run_plu
	@echo "Running diff to validate outputs"
	@for f in $$(ls ${OUTPUT_PATH});do \
		diff -a -q ${OUTPUT_PATH}$$f/run_plugin/part-00000 ${OUTPUT_PATH}$$f/run_reg/part-00000; \
	done
	@echo "End diff to validate outputs"

run: setup run_diff

setup: clean
	@mkdir -p ${CLASSES_PATH}
	@mkdir -p ${ARTIFACTS_PATH}

clean:
	@rm -rf ${OUT_ROOT}

make_subset:
	cd input &&  \
	head -100001 all/similar_artists.csv > similar_artists.csv && \
	gzip -f similar_artists.csv
