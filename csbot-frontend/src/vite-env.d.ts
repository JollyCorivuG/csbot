declare module '*.vue' {
    import {DefineComponent} from "vue"
    const component: DefineComponent<{}, {}, any>
    export default component
}

interface ImportMetaEnv {
    VITE_API_PREFIX: string,
    VITE_WS_URL: string,
    VITE_STATIC_ASSETS_PATH: string
}