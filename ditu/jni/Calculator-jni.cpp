#include <jni.h>
extern "C"{
	jstring JNICALL Java_com_example_toolbox_MyActivity_getMoon
	(JNIEnv * env, jobject obj)
	{
		return env->NewStringUTF("Hello Moon");
	}
	jdouble Java_com_example_toolbox_CalActivity_calculateFromJNI(
			JNIEnv* env, jobject thiz, jdouble a, jdouble b, jchar op){
		switch(op){
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a*b;
		case '/':
			return a/b;
		}
	}
}
