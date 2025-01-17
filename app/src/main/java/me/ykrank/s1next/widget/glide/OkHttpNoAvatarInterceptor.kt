package me.ykrank.s1next.widget.glide

import me.ykrank.s1next.data.api.Api
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.Response.Builder
import java.io.IOException

/**
 * Created by ykrank on 2017/3/6.
 */
class OkHttpNoAvatarInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request: Request = chain.request()
        val url = request.url.toString()
        return if (NO_AVATAR_URLS.contains(url)) {
            Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(504)
                .message("Empty avatar image") //                    .body(Util.EMPTY_RESPONSE)
                .sentRequestAtMillis(-1L)
                .receivedResponseAtMillis(System.currentTimeMillis())
                .build()
        } else chain.proceed(request)
    }

    companion object {
        private const val NO_AVATAR_SMALL_PREFIX = "images/noavatar_small.gif"
        private const val NO_AVATAR_MIDDLE_PREFIX = "images/noavatar_middle.gif"
        private const val NO_AVATAR_BIG_PREFIX = "images/noavatar_big.gif"
        private const val AVATAR_HOST = "avatar.saraba1st.com"
        private val NO_AVATAR_PREFIXES = arrayOf(
            NO_AVATAR_SMALL_PREFIX, NO_AVATAR_MIDDLE_PREFIX, NO_AVATAR_BIG_PREFIX
        )
        private val NO_AVATAR_URLS = initNoAvatarUrls()
        private fun initNoAvatarUrls(): List<String> {
            val list: MutableList<String> = ArrayList()
            for (prefix in NO_AVATAR_PREFIXES) {
                //eg http://avatar.saraba1st.com/images/noavatar_small.gif
                list.add(String.format("http://%s/%s", AVATAR_HOST, prefix))
                if (Api.SUPPORT_HTTPS) {
                    list.add(String.format("https://%s/%s", AVATAR_HOST, prefix))
                }
                for (host in Api.HOST_LIST) {
                    //eg http://bbs.saraba1st.com/uc_server/images/noavatar_small.gif
                    list.add(String.format("http://%s/uc_server/%s", host, prefix))
                    //eg http://bbs.saraba1st.com/2b/uc_server/images/noavatar_small.gif
                    list.add(String.format("http://%s/2b/uc_server/%s", host, prefix))
                    if (Api.SUPPORT_HTTPS) {
                        //eg https://bbs.saraba1st.com/uc_server/images/noavatar_small.gif
                        list.add(String.format("https://%s/uc_server/%s", host, prefix))
                        //eg https://bbs.saraba1st.com/2b/uc_server/images/noavatar_small.gif
                        list.add(String.format("https://%s/2b/uc_server/%s", host, prefix))
                    }
                }
            }
            return list
        }
    }
}
