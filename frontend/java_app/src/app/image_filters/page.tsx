import { link } from "fs";
import Image from "next/image";
import Link from "next/link";

export default function imageFilters() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div className="z-10 max-w-5xl w-full items-center justify-between font-mono text-sm lg:flex">
        <p className="fixed left-0 top-0 flex w-full justify-center border-b border-gray-300 bg-gradient-to-b from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto  lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30">
          Happy filtering!&nbsp;
          <code className="font-mono font-bold">java</code>
        </p>
        <div className="fixed bottom-0 left-0 flex h-48 w-full items-end justify-center bg-gradient-to-t from-white via-white dark:from-black dark:via-black lg:static lg:h-auto lg:w-auto lg:bg-none">
          <Link
            href="/"
            className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
            rel="noopener noreferrer"
          >
            <Image
              className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
              src="/logo.svg"
              alt="App.js Logo"
              width={180}
              height={37}
              priority
            />
          </Link>
        </div>
      </div>

      <div className="text text-center">
        <a
          className="group rounded-lg border border-transparent px-5 py-4 transition-colors"
        >
          <h2 className={`mb-3 text-2xl font-semibold`}>
            Try the tools below
          </h2>
          <p className={`m-0 max-w-[30ch] text-sm opacity-50`}>
            All tools are developed in Java
          </p>
        </a>
      </div>

      <div className="mb-32 flex justify-center lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-4 lg:text-left">

        <Link
          href="/image_filters/blur"
          className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
          rel="noopener noreferrer"
        >
          <h2 className={`mb-3 text-2xl font-semibold`}>
            Blur{" "}
            <span className="inline-block transition-transform group-hover:translate-x-1 motion-reduce:transform-none">
              -&gt;
            </span>
          </h2>
          <p className={`m-0 max-w-[30ch] text-sm opacity-50`}>
            Blur your image
          </p>
        </Link>

        <Link
            href="/image_filters/threshold"
            className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
            rel="noopener noreferrer"
        >
            <h2 className={`mb-3 text-2xl font-semibold`}>
                Threshold{" "}
                <span className="inline-block transition-transform group-hover:translate-x-1 motion-reduce:transform-none">
                -&gt;
                </span>
            </h2>
            <p className={`m-0 max-w-[30ch] text-sm opacity-50`}>
                Turn images black and white
            </p>
        </Link>
      </div>

      <div className="mb-32 flex justify-left lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-4 lg:text-left">
        <Link
            href="/"
            className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
            rel="noopener noreferrer"
          >
            <h2 className={`mb-3 text-2xl font-semibold`}>
              <span className="inline-block transition-transform group-hover:translate-x-1 motion-reduce:transform-none" style={{ transform: 'scaleX(-1)' }}>
                -&gt;
              </span>
              Go back{" "}
            </h2>
          </Link>
      </div>
    </main>
  );
}
