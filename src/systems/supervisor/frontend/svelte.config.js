// svelte.config.js

// import adapter from '@sveltejs/adapter-auto';
import adapter from '@sveltejs/adapter-node';
import { vitePreprocess } from '@sveltejs/kit/vite';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		adapter: adapter(),
		paths: {
			base: "",
			assets: ""
		},
	},

	vitePlugin: {
		experimental: {
			// Allows you to hold ctrl+shift and click on an item in the browser and it then
			// opens that components location in VSCode
			inspector: {
				holdMode: true
			}
		}
	},
	preprocess: [vitePreprocess()]
};

export default config;