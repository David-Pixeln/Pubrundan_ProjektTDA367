import { StyleSheet } from "react-native";
import {CustomTheme, DarkTheme} from '@constants/themes';


export const styles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    width: "80%",
    bottom: 16,
    
    position: "absolute",
    alignSelf: "center",

    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",

    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 20,

    backgroundColor: theme.colors.background.brand,
  },
  button: {
    display: 'flex',
    justifyContent: 'center',
    gap: 4,
  },
  iconWrapper: {
    width: 50,
    aspectRatio: 1,
    borderRadius: 50,

    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    
    backgroundColor: theme.colors.background.s600,

    zIndex: 1,
  },
  iconWrapperPressed: {
    backgroundColor: theme.colors.background.s200,
  },
  text: {
    fontSize: 10,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
