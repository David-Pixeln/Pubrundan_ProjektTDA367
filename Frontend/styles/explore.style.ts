import { CustomTheme } from '@constants/themes';
import { StyleSheet } from 'react-native';



export const ExplorePageStyles = (theme: CustomTheme) => StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
  button1: {
    width: 52,
    height: 52, 
    transform: [{translateX: 10}, {translateY: -650}],
    elevation: 3,
    justifyContent: 'center',
    borderRadius: 100,
    backgroundColor: theme.colors.background.s600,
  },
  button2: {
    width: 52,
    height: 52, 
    transform: [{translateX: 300}, {translateY: -702}],
    elevation: 3,
    justifyContent: 'center',
    borderRadius: 100,
    backgroundColor: theme.colors.background.s600,
  },
  icon: {
    postition: "relative",
    transform: [{translateX: 11.5}, {translateY:0}],
    justifyContent: 'center',
    opacity: 1,
    bottom: 0,
    elevation: 3,
  },
  searchIcon: {
    postition: "relative",
    transform: [{translateX: 0}, {translateY:11}, {rotate: '90deg'}],
    justifyContent: 'center',
    opacity: 1,
    bottom: 0,
    elevation: 3,
  },
});