"use client"

import { link } from "fs";
import Image from "next/image";
import Link from "next/link";
import reqThreshold from "../helpers.js";
import React, { Component, useState } from "react";

var image = null

export default function blur() {
  const [selectedImage, setSelectedImage] = useState(null);
  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <div className="z-10 max-w-5xl w-full items-center justify-between font-mono text-sm lg:flex">
        <p className="fixed left-0 top-0 flex w-full justify-center border-b border-gray-300 bg-gradient-to-b from-zinc-200 pb-6 pt-8 backdrop-blur-2xl dark:border-neutral-800 dark:bg-zinc-800/30 dark:from-inherit lg:static lg:w-auto  lg:rounded-xl lg:border lg:bg-gray-200 lg:p-4 lg:dark:bg-zinc-800/30">
          Happy blurring!&nbsp;
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
            Blur your image
          </h2>
        </a>
      </div>

      <div className="mb-32 flex justify-center lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-4 lg:text-left">
        {selectedImage && (
          <div>
            <div className='imageInput'>
              <img 
                id='image'
                alt="not found"
                src={URL.createObjectURL(selectedImage)}
                style={{maxWidth: '40vw', maxHeight: '40vh'}}
              />
            </div>
          </div>
        )}
      </div>
      <div className="mb-32 flex justify-center lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-4 lg:text-left">
        <label id="upload" className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30">
          <input
            type="file"
            style={{ display: 'none' }} // Hide the input using inline styles
            name="myImage"
            onChange={(event) => {
              //Get uploaded image
              image = event.target.files[0];
              if(image != undefined){  
                setSelectedImage(event.target.files[0]);
                document.getElementById("submit").style.display = "flex";
              }
            }}
          />
          <p>Upload image</p>
          <Image 
            className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
            src="/upload.svg"
            alt="Upload"
            style={{ marginLeft: 'auto', marginRight: 'auto' }}
            width={50}
            height={37}
          />
        </label>

        <button id="submit" className="group rounded-lg border border-transparent px-5 py-4 transition-colors hover:border-gray-300 hover:bg-gray-100 hover:dark:border-neutral-700 hover:dark:bg-neutral-800/30"
          style={{ display: 'none' }} // Hide the button using inline styles
          onClick={() => reqThreshold(image)}>Filter Image{" "}
          <Image 
            className="relative dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
            src="/submit.svg"
            alt="submit"
            style={{ marginLeft: 'auto', marginRight: 'auto' }}
            width={50}
            height={37}
          />
         </button>
         
      </div>

      <div className="mb-32 flex justify-left lg:max-w-5xl lg:w-full lg:mb-0 lg:grid-cols-4 lg:text-left">
        <Link
            href="/image_filters"
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
