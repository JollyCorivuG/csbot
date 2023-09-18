<template>
    <div class="login-form-wrapper">
        <div class="login-form-title">
            <svg x="1694491332818" class="icon" viewBox="0 0 1073 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2695" width="40" height="40"><path d="M264.721092 483.008277a78.583566 78.583566 0 1 0 78.583566-78.583566 78.134517 78.134517 0 0 0-78.583566 78.583566z m407.90484-78.583566a78.583566 78.583566 0 1 0 78.583566 78.583566 79.25714 79.25714 0 0 0-78.583566-78.583566z m357.892014 16.0535h-5.613112c-53.549087-185.681741-221.381132-269.42937-292.836047-303.557091-3.592392-1.796196-5.613112-3.592392-8.924848-5.613111a79.313271 79.313271 0 0 0 23.238283-37.495588 52.931645 52.931645 0 0 0-14.257304-48.216631c-10.721044-12.517239-33.678671-37.495587-171.424437-19.645891C426.771632 20.207203 110.753433 127.361509 48.223367 420.197555H41.094715a40.414406 40.414406 0 0 0-41.087979 41.087979v130.336458a40.414406 40.414406 0 0 0 41.087979 41.087979h7.128652c46.420435 228.565916 239.286959 328.535438 424.9687 348.012936v1.796196a40.414406 40.414406 0 0 0 41.087979 41.087979h167.832045a40.414406 40.414406 0 0 0 41.087979-41.087979v-97.780409a40.414406 40.414406 0 0 0-41.087979-41.087979h-167.832045a40.414406 40.414406 0 0 0-41.087979 41.087979v5.613112c-107.154306-12.517239-287.447459-66.066327-335.66409-262.469111a44.904895 44.904895 0 0 0 23.238283-37.495588V459.769994a40.63893 40.63893 0 0 0-21.105301-35.699392c71.398783-278.522611 428.504961-330.331634 432.209615-332.127829 23.238283-3.592392 41.087979-3.592392 57.141479-5.613112a74.036946 74.036946 0 0 0-5.613112 28.570739 72.353012 72.353012 0 0 0 28.570739 55.345283 168.393356 168.393356 0 0 0 42.828044 24.978348c62.473935 30.366935 190.845804 94.637066 239.286959 228.565916a40.63893 40.63893 0 0 0-21.442087 35.699391v130.336458a40.414406 40.414406 0 0 0 41.087979 41.087979h78.583566a40.414406 40.414406 0 0 0 41.087979-41.087979V459.769994a42.884175 42.884175 0 0 0-42.884174-39.291783z" fill="#333333" p-id="2696"></path></svg>
            <span>Cs Bot</span>
        </div>
        <a-form
            class="login-form"
            layout="vertical"
            :model="loginForm"
        >
            <a-form-item
                field="phone"
                :validate-trigger="['change', 'blur']"
                hide-label
                :rules="rules.phone"
            >
                <a-input
                    placeholder="请输入手机号"
                    allow-clear
                    v-model="loginForm.phone"
                >
                    <template #prepend>
                        +86
                    </template>
                </a-input>
            </a-form-item>
            <a-form-item
                field="captcha"
                :validate-trigger="['change', 'blur']"
                hide-label
                :rules="rules.captcha"
            >
                <a-input
                    allow-clear
                    placeholder="请输入验证码"
                    v-model="loginForm.captcha"
                >
                </a-input>
                <div class="captcha">
                    <a-image
                        :preview="false"
                        :width="captcha.width"
                        :height="captcha.height"
                        :src="captcha.base64"
                        @click="refreshCaptcha"
                    />
                </div>
            </a-form-item>
            <a-space :size="16" direction="vertical">
                <div class="login-form-password-actions">
                    <a-checkbox
                        default-checked
                    >
                        记住账号
                    </a-checkbox>
                    <a-link>找回账号</a-link>
                </div>
                <a-button type="primary" html-type="submit" long @click="login" :loading="loading">
                    登录
                </a-button>
            </a-space>
        </a-form>
    </div>

</template>

<script setup lang="ts">
import {ref} from "vue";
import {CaptchaInfo, CaptchaResponse, LoginParams} from "@/api/user/type.ts";
import {Message} from "@arco-design/web-vue";
import {reqCaptcha} from "@/api/user";
import {CommonResponse} from "@/api/auth/type.ts";
import useUserStore from "@/pinia/modules/user";
import {useRouter} from "vue-router";

// 表单验证
const loginForm = ref<LoginParams>({
    phone: '18611555833',
    captchaId: 0,
    captcha: ''
})
const rules = {
    phone: [{
        validator: (value: string, cb: (error?: string) => void): void => {
            if (!/^1[3456789]\d{9}$/.test(value)) {
                cb('请输入正确的手机号')
                return
            }
            cb()
        }
    }],
    captcha: [{
        validator: (value: string, cb: (error?: string) => void): void => {
            // 用正则表达式表示4位数字或字母
            if (!/^[a-zA-Z0-9]{4}$/.test(value)) {
                cb('请输入正确的验证码')
                return
            }
            cb()
        }
    }],
}

// 验证码信息
const captcha = ref<CaptchaInfo>({
    id: 0,
    base64: '',
    width: 0,
    height: 0
})
const getCaptcha = async () => {
    const resp: CaptchaResponse = await reqCaptcha()
    if (resp.statusCode != 0) {
        Message.error(resp.statusMsg)
        return
    }
    captcha.value = resp.data
    loginForm.value.captchaId = captcha.value.id
}
getCaptcha()
const refreshCaptcha = async () => {
    await getCaptcha()
}

// 登录
const userStore = useUserStore()
const loading = ref<boolean>(false)
const router = useRouter()
const login = async () => {
    if (loginForm.value.captcha.length != 4 || !/^1[3456789]\d{9}$/.test(loginForm.value.phone)) {
        Message.error('请检查表单正确性！')
        return
    }
    loading.value = true
    const resp: CommonResponse = await userStore.login(loginForm.value)
    if (resp.statusCode != 0) {
        Message.error(resp.statusMsg)
        loading.value = false
        return
    }
    await router.push('/index')
    Message.success('登录成功！')
    loading.value = false
}
</script>

<style lang="scss" scoped>
.login-form-wrapper {
    width: 320px;


    .login-form-title {
        font-weight: 500;
        font-size: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        span {
            margin-left: 10px;
        }
        margin-bottom: 20px;
    }

    .login-form-error-msg {
        height: 32px;
        color: rgb(var(--red-6));
        line-height: 32px;
    }

    .login-form-password-actions {
        display: flex;
        justify-content: space-between;
    }

    .login-form-register-btn {
        color: var(--color-text-3) !important;
    }
    .captcha {
        cursor: pointer;
        margin-left: 15px;
    }
}
</style>