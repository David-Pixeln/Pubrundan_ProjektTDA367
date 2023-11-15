export type Neutral =
  | "s000"
  | "s100"
  | "s150"
  | "s200"
  | "s250"
  | "s300"
  | "s400"
  | "s500"
  | "s600"
  | "s700"
  | "s800"
  | "s900"
  | "s1000"
export const defaultNeutral: Record<Neutral, string> = {
  s000: "#ffffff",
  s100: "#efeff6",
  s150: "#dfdfe6",
  s200: "#c7c7ce",
  s250: "#bbbbc2",
  s300: "#9f9ea4",
  s400: "#7c7c82",
  s500: "#515154",
  s600: "#38383a",
  s700: "#2d2c2e",
  s800: "#212123",
  s900: "#161617",
  s1000: "#000000",
}

export type ShadedColor = "brand" | "s200" | "s600"
export const defaultShadedColor = (brandColor: string): Record<ShadedColor, string> => ({
  s200: shadeColor(brandColor, 20),
  brand: brandColor,
  s600: shadeColor(brandColor, -20)
})

export const applyOpacity = (hexColor: string, opacity: number): string => {
  const red = parseInt(hexColor.slice(1, 3), 16)
  const green = parseInt(hexColor.slice(3, 5), 16)
  const blue = parseInt(hexColor.slice(5, 7), 16)

  return `rgba(${red}, ${green}, ${blue}, ${opacity})`
}

export const shadeColor = (hexColor: string, percent: number): string => {
  const redGamut: number = parseInt(hexColor.slice(1, 3), 16)
  const greenGamut: number = parseInt(hexColor.slice(3, 5), 16)
  const blueGamut: number = parseInt(hexColor.slice(5, 7), 16)

  const rgb: Array<number> = [redGamut, greenGamut, blueGamut]

  const toShadedGamut = (gamut: number): number => {
    return Math.floor(Math.min(gamut * (1 + percent / 100), 255))
  }

  const toHex = (gamut: number): string => {
    return gamut.toString(16).length === 1
      ? `0${gamut.toString(16)}`
      : gamut.toString(16)
  }

  const shadedRGB: Array<number> = rgb.map(toShadedGamut)
  const shadedHex: Array<string> = shadedRGB.map(toHex)

  const hexString: string = shadedHex.join("")

  return `#${hexString}`
}