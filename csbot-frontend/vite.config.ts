import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// 引入node提供的path模块
import path from 'path'
export default defineConfig({
  	plugins: [vue()],
	resolve: {
		alias: {
			'@': path.resolve(__dirname, 'src')
		}
	}
})
